#!/bin/python3

import re

PATH = '../TeamCode/'

# regex
start_re = re.compile(r'startPosition\wr*=\w*new Pose\()')

start_pos = []


def get_file():
    file = open(PATH)
    return file.read()


def main():
    text = get_file()
    start_lines = start_re.findall(text)

    pass


if __name__ == '__main__':
    main()
