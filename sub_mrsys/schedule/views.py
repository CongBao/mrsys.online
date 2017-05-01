# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse

import commands
import os

def index(request):
    t = open('t','w')
    t.write(str(request.GET))
    t.close()
    if 'h' in request.GET and 'm' in request.GET and 's' in request.GET:
        h = request.GET['h']
        m = request.GET['m']
        s = request.GET['s']
        temp = open('/home/Scheduler/log/configlog','wb')
	temp.write(str(request.GET))
	temp.close()
        if 'demo' in request.GET and request.GET['demo'] == 'true':
            config = open('/home/Scheduler/configuration','wb+')
            config.write('demo=true')
            config.close()
	    temp = open('/home/Scheduler/log/config-log','a')
	    temp.write('views set it to true')
	    temp.close()
        kill_process()
        os.chdir('/home/Scheduler')
        command = 'nohup java -jar Scheduler.jar '+h+':'+m+':'+s+' &'
        print command
        os.system(command)
    return HttpResponse('New Schedule Time: '+h+':'+m+':'+s)

def kill_process():
    output = commands.getoutput('pgrep -f Scheduler.jar')
    for i in output.split('\n'):
        os.system('kill '+i)
# Create your views here.
