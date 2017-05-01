import sys
import json
import os
import urllib2
import requests 
from time import sleep
import logging
import logging.handlers
# read command
command = sys.argv[1]

# initialize logging system
if not os.path.exists('log'):
    os.mkdir('log')
today = command.split('@')[0]
LOG_FILE = 'log/demo-' + today + '.log'
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

# read configuration
config = open('/home/Scheduler/configuration', 'r')
info = config.readline()
config.close()
demo_or_not = (info.split('=')[1]).strip('\n')
logging.info('Demo = ' + str(demo_or_not))
if demo_or_not == 'true':
    logging.info('Demo started')
    # package results into json immediately
    folder_name = command.split('@')[0]
    os.chdir('/home/Scheduler/res/'+folder_name)
    rest = command.split('@')[1]
    target_users = rest.split('#')
    output = {}
    logging.info('Target users are ' + str(target_users))
    for userid in target_users:
        if not userid == "":
            res = open(userid+'.result','r')
            arr = []
            while True:
                i = res.readline().strip('\n')
                print i
                if not i == '':
                    arr.append(i)
                else:
                    break
            res.close()  
            output[userid] = arr
            logging.info("Got the list of User " + str(userid) + ": " + str(arr))
    result = {'updates':output}
    logging.info("The message that needs to be sent: " + str(result))
    #send the json package
    url = 'https://www.mrsys.online/ajax/updateRecom'
    headers = {'content-type':'application/json'}
    try:
        sleep(10)
        r = requests.post(url, json=result)
        logging.info("Sent successfully")
    except Exception,e:
        logging.error("Failed")
        logging.exception(e)
# change 'demo' back to false
config = open('/home/Scheduler/configuration', 'wb+')
config.write('demo=false')
config.close()
