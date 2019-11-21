'''
Module with functions for inserting data into the database
'''

import random
import time
from presets import *

def str_time_prop(start, end, format):
    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))
    ptime = stime + random.random() * (etime - stime)

    return time.strftime(format, time.localtime(ptime))

def generateRandomDate():
    start = "1990-1-1"
    end = "2010-12-25"
    return str_time_prop(start, end, '%Y-%m-%d')

def insertCompanies(cursor):
    for c in companies:
        cursor.execute("INSERT INTO Companies VALUES('" + c + "')")

def insertRoles(cursor):
    for r in wroles.keys():
        cursor.execute("INSERT INTO WRole VALUES('" + r + "', '" + wroles[r] + "')")

def insertBadges(cursor):
    for b in badges.keys():
        cursor.execute("INSERT INTO Badges VALUES('" + b + "', '" + badges[b] + "')")

def insertSkills(cursor):
    for s in skills:
        cursor.execute("INSERT INTO Skills VALUES('" + s + "')")

def insertUsers(groupCount, cursor):
    addr = ["gmail", "yahoo", "hotmail"]
    ctr = ["ro", "com", "co.uk", "hu"]
    i = 1
    for name in names:
        for surname in surnames:
            date = generateRandomDate()
            print(date)
            gid = random.randint(1, groupCount)
            email = name.lower() + surname.lower() + str(int(date.split("-")[0]) % 100) + "@" + random.choice(addr) + "." + random.choice(ctr)
            cursor.execute("INSERT INTO Users VALUES({}, '{}', '{}', '{}', '{}', {})".format(i,name,surname,email,date,gid))
            i += 1

def populateDB(groupCount, cursor):
    insertBadges(cursor)
    insertCompanies(cursor)
    insertRoles(cursor)
    insertSkills(cursor)
    insertUsers(groupCount, cursor)







