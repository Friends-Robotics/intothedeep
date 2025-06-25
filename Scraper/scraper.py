#!/bin/python3

import re

PATH = '../TeamCode/'

# regex
start_re = re.compile(r'startPosition\wr*=\w*new Pose\()')

def get_file():
    file = open(PATH)
    return file.read()

def main():
    text = get_file()

    


if __name__ == '__main__':
    main()
