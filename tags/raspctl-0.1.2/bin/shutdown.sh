#!/bin/sh
ps ux | grep -i 'raspctl.py' | grep -i 'python' | awk '{print $2}' | xargs kill -9
