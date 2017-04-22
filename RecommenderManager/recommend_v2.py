# -*- coding=utf-8 -*-
from __future__ import division

import os
import sys
import time
import re
import numpy as np
from scipy import spatial
import math
import logging
import logging.handlers

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
    """
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
    logging.info(str(time.clock() - start_time) + " used to generate the dictionary.")
    del ratings
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
        logging.info(str(counter) + "/" + str(movie_num))
    logging.info(str(i) + " neighbors found in total.")
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
    # firstly, find the K nearest neighbors
    temp = time.clock()
    neighbors = getNeighborSimilarity(userid, user_to_rating_dic, movie_to_user_dic)
    logging.info(str(time.clock() - temp) + "used to find all the neighbors.")
    # secondly, estimate the target user's ratingsS
    recommend_dic = {}  # neighbors is the list of [similarity, neighbor_id]
    similarity_dic = {}
    count_dic = {}  # the number of neighbors that contributed to the estimation of each movie's rating
    for neighbor in neighbors:  # neighbors is the list of [similarity, neighbor_id]
        neighbor_user_id = neighbor[1]
        movies = user_to_rating_dic[
            neighbor_user_id]  # the list of this neighbor, each element in this array is (movie_id, rating_value)
        for movie in movies:
            if movie[0] not in recommend_dic:
                recommend_dic[movie[0]] = neighbor[0] * movie[1]
                similarity_dic[movie[0]] = neighbor[0]
                count_dic[movie[0]] = 1
            else:
                recommend_dic[movie[0]] += neighbor[0] * movie[1]
                similarity_dic[movie[0]] += neighbor[0]
                count_dic[movie[0]] += 1
    for key in recommend_dic:
        recommend_dic[key] = recommend_dic[key] / similarity_dic[key]
    # thirdly, build the recommendation list
    result_list = sorted(recommend_dic.items(), lambda x, y: cmp(x[1], y[1]), reverse=True)
    result_name = 'res/' + today + '/' + str(userid) + '.result'
    result = file(result_name, 'wb+')
    list_length = len(result_list)
    movie_count = 0  # we only need 1000 valid recommendations
    target_user_rated_movies = [movie[0] for movie in user_to_rating_dic[target_user]]
    for i in range(list_length):
        movie_id = result_list[i][0]
        if not movie_id in target_user_rated_movies and count_dic[movie_id] >= 100:
            result.write(str(int(movie_id)))
            result.write('&' + str(count_dic[movie_id]) + '\n')
            movie_count += 1
            if movie_count >= 100:
                break
    result.close()
    logging.info("Recommendation for user " + str(userid) + " succeeded.")
    logging.info("The result of User " + str(userid) + " has been written to the file " + result_name)
    return result_list
    # return [i[0] for i in recommend_list]  # return movie ids according to their estimated ratings (high to low)


# the main function
if __name__ == '__main__':
    # system setting
    start_time = time.clock()
    reload(sys)
    sys.setdefaultencoding('utf-8')

    # prepare phase
    if not os.path.exists('res'):
        os.mkdir('res')
    if not os.path.exists('res/' + today):
        os.mkdir('res/' + today)
    if not os.path.exists('log'):
        os.mkdir('log')
    LOG_FILE = 'log/py-' + today + '.log'
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    file_handler = logging.FileHandler(LOG_FILE, mode='a')
    file_handler.setLevel(logging.INFO)
    console_handler = logging.StreamHandler()
    console_handler.setLevel(logging.INFO)
    formatter = logging.Formatter('%(asctime)s %(filename)s[line:%(lineno)d] [%(levelname)s] %(message)s')
    file_handler.setFormatter(formatter)
    console_handler.setFormatter(formatter)
    logger.addHandler(file_handler)
    logger.addHandler(console_handler)

    # analyze command
    try:
        command = sys.argv[1]
    except IndexError:
        logging.critical("No command received.")
        command = today + '@87854#'
    given_date = command.split('@')[0]
    target_users = re.findall('(\d+)#', command)
    if given_date != time.strftime("%Y-%m-%d", time.localtime()):
        logging.critical('Inconsistent date')
        sys.exit()
    logging.info("Recommendation service started.")

    # read data
    logging.info('Start to read ratings.')
    file_name = "data/ratings2000.csv"
    try:
        ratings = getRatings(file_name)
    except Exception as e:
        logging.exception(e)
    # logging.info(str(time.clock() - start_time) + "used to read the ratings.")
    # generate dictionaries
    user_to_rating_dic, movie_to_user_dic = getDictionaries(ratings)

    # call recommend function
    i = 0
    while time.strftime("%Y-%m-%d", time.localtime()) == given_date and i < len(target_users):
        target_user = int(target_users[i])
        start_time = time.clock()
        logging.info("Start to generate the recommendation list for user " + str(target_user))
        try:
            recommend_list = recommend(target_user)
        except Exception as e:
            logging.exception(e)
            logging.error("Recommendation for user " + str(target_user) + " failed.")
        logging.info(str(time.clock() - start_time) + " seconds used in total.")
        i += 1
