# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse

import commands
import os

def index(request):
    if 'h' in request.GET and 'm' in request.GET and 's' in request.GET:
        h = request.GET['h']
        m = request.GET['m']
        s = request.GET['s']
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
