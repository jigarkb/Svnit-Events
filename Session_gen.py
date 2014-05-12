##Session generator
import sys
import json
from collections import OrderedDict
d=OrderedDict()

d["web_url"]=input("Enter Web URL")
d["end_time_epoch"]=str(input("Enter end epoch time"))
d["title"]=str(input("Enter Title of event"))
d["peoples"]=[]
d["space"]=str(input("Enter location of event"))
d["start_time_epoch"]=str(input("Enter start epoch time"))
d["abstract"]=str(input("Enter Abstract of event"))

print json.dumps(d)
