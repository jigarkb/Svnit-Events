##Session generator
import sys
import json
from collections import OrderedDict
d=OrderedDict()

d["name"]=str(input("Enter name pf person"))
d["role"]=str(input("Enter role of person"))
d["twitter"]=str(input("Enter twitter handle"))
d["image_75"]=str(input("Enter image url"))
d["people_bio"]=str(input("Enter bio"))
d["contact_no"]=str(input("Enter contact no"))

print json.dumps(d)
