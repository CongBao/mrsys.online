# -*- coding=utf-8 -*-
from __future__ import division

import os
import sys
import time
import re
import numpy as np
from scipy import spatial
import math

user_path = ""
try:
    user_path = os.environ['USERPROFILE']
except KeyError:
    try:
        user_path = os.environ['HOMEPATH']
    except KeyError:
        user_path = os.environ['HOME']

today = time.strftime("%Y-%m-%d", time.localtime())


# calculate the similarity between two users - no problem
def getSimilarity(user1, user2):
    sum_x = 0.0
    sum_y = 0.0
    sum_xy = 0.0
    for key1 in user1:
        for key2 in user2:
            if key1[0] == key2[0]:
                sum_xy += key1[1] * key2[1]
                sum_y += key2[1] * key2[1]
                sum_x += key1[1] * key1[1]
    if sum_xy == 0.0:
        return 0
    sx_sy = math.sqrt(sum_x * sum_y)
    return sum_xy / sx_sy
    """
    rating1 = []
    rating2 = []
    for key1 in user1:
        for key2 in user2:
            if key1[0] == key2[0]:
                rating1.append(key1[1])
                rating2.append(key2[1])
    return (1 - spatial.distance.cosine(rating1, rating2))
    """


#
#   create the data structures
#
#   user_rate_dic - key is user_id, value is a list of his ratings, each element in the list is (movie_id, rating_value) 
#   movie_to_user - key is movie_id, value is the user_ids of who have rated this movie
#
#   0 - user id / 1 - movie id / 2- rating
#
def getDictionaries(ratings):
    start_time = time.clock()
    user_rate_dic = {}
    movie_to_user = {}
    for i in ratings:  # each row in ratings is i[0] - user_id, i[1] - movie_id, i[2] - the rating value
        user_rank = (i[1], i[2])  # user_rank is (movie_id, rating_value)
        if i[0] in user_rate_dic:  # if this user already has his/her list, just append this ratings to the list
            user_rate_dic[i[0]].append(user_rank)
        else:  # if there is no list of this user, create a new one, the first element is user_rank
            user_rate_dic[i[0]] = [user_rank]
        if i[1] in movie_to_user:
            movie_to_user[i[1]].append(i[0])  # it's gonna be used when finding all the neighbors
        else:
            movie_to_user[i[1]] = [i[0]]
    print "Dic = " + str(time.clock() - start_time)
    return user_rate_dic, movie_to_user  # return these two dictionaries


#
#   Find the nearest neighbors
#   Input: the id of the specific user, all the data of users, all th data of movies
#   Ouput: the nearest neighbors of this user
#
def getNeighborSimilarity(user_id, users_dic, movie_dic):
    # firstly, find all the neighbors
    i = 0
    neighbors = []
    movie_num = len(users_dic[user_id])  # item_num is the number of movies this user has rated
    counter = 0
    for movie_rating in users_dic[user_id]:  # for each movie that the user has rated
        movie_id = movie_rating[0]  # movie_id is this movie's id
        for neighbor_id in movie_dic[movie_id]:  # add everyone who has watched this movie to the neighbor list
            if neighbor_id != user_id and neighbor_id not in neighbors:  # does not contain this user himself and the ids that have already been added to the list
                neighbors.append(neighbor_id)
                i = i + 1  # count the number of neighbors in total
        counter = counter + 1  # how many movies are finished
        print counter, "/", movie_num
    print i, "neighbors found in total."
    neighbors_dist = []  # calculate the distance between the user and each neighbor of him/her
    for neighbor_id in neighbors:
        dist = getSimilarity(users_dic[user_id], users_dic[neighbor_id])
        neighbors_dist.append([dist, neighbor_id])  # add the id-distance entry to the new dictionary
    neighbors_dist.sort(reverse=True)  # sort the dictionary
    return neighbors_dist  # the list of [similarity, neighbor_id]


def getRatings(file_name):
    return np.loadtxt(file_name, dtype=float, delimiter=",")


# by default, K=5
def recommend(userid):
    # thirdly, find the K nearest neighbors
    temp = time.clock()
    neighbors = getNeighborSimilarity(userid, user_to_rating_dic, movie_to_user_dic)
    print len(neighbors)
    print time.clock() - temp, "used to find the K nearest neighbors"
    # fourthly, estimate the target user's rating on every movie that he/she hasn't rated
    recommend_dic = {}  # neighbors is the list of [similarity, neighbor_id]
    similarity_dic = {}
    for neighbor in neighbors:  # neighbors is the list of [similarity, neighbor_id]
        neighbor_user_id = neighbor[1]
        movies = user_to_rating_dic[
            neighbor_user_id]  # the list of this neighbor, each element in this array is (movie_id, rating_value)
        for movie in movies:
            if movie[0] not in recommend_dic:
                recommend_dic[movie[0]] = neighbor[0] * movie[1]
                similarity_dic[movie[0]] = neighbor[0]
            else:
                recommend_dic[movie[0]] += neighbor[0] * movie[
                    1]  # each movie in the recommend_dic dictionary has a value, which determine how much it will be recommended
                similarity_dic[movie[0]] += neighbor[0]
    for key in recommend_dic:
        recommend_dic[key] = recommend_dic[key] / similarity_dic[key]
    # finally, build the recommendation list - recommend_dic: key-movie_id, recommend_dic[key]-estimated rating value on this movie
    recommend_list = sorted(recommend_dic.items(), lambda x, y: cmp(x[1], y[1]), reverse=True)
    if os.path.exists('res') == False:
        os.mkdir('res')
    if os.path.exists('res/' + today) == False:
        os.mkdir('res/' + today)
    result_name = 'res/' + today + '/' + str(userid) + '.result'
    result = file(result_name, 'wb+')
    list_length = min(len(recommend_list), 1000)
    for i in range(list_length):
        result.write(str(int((recommend_list[i])[0])) + '\n')
    result.close()
    print "The result of User", userid, " has been written to the file", result_name
    return recommend_list
    # return [i[0] for i in recommend_list]  # return movie ids according to their estimated ratings (high to low)

#	the main function
if __name__ == '__main__':
    # system setting
    start_time = time.clock()
    reload(sys)
    sys.setdefaultencoding('utf-8')

    # analyze command
    command = sys.argv[1]
    given_date = command.split('@')[0]
    target_users = re.findall('(\d+)#',command)
    if given_date != time.strftime("%Y-%m-%d", time.localtime()):
        print "Wrong date!"
        print given_date
        print time.strftime("%Y-%m-%d", time.localtime())
        sys.exit()

    # read data
    file_name = "data/ratings_after_2000.csv"
    ratings = getRatings(file_name)
    print time.clock() - start_time, "used to read the ratings."
    # generate dictionaries
    user_to_rating_dic, movie_to_user_dic = getDictionaries(ratings)

    # call recommend function
    i = 0
    while time.strftime("%Y-%m-%d", time.localtime()) == given_date and i < len(target_users):
        target_user = int(target_users[i])
        start_time = time.clock()
        print "Start to generate the recommendation list for user " + str(target_user)
        recommend_list = recommend(target_user)
        print time.clock() - start_time, "seconds used in total."
        i += 1