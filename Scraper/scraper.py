#!/bin/python3

import re
import json


PATH = '../TeamCode/src/main/java/friends/autonomous/FourSpecimen.java'

# regex
start_re = re.compile(r'startPose\W*=\W*new\W*Pose\((\d+\.\d+), (\d+\.\d+)')

def get_file():
    file = open(PATH)
    return file.read()

def get_start_pose(text):
    start_x, start_y = start_re.findall(text)[0]
    return start_x, start_y

def main():
    text = get_file()
    start_x, start_y = get_start_pose(text)



if __name__ == '__main__':
    main()
