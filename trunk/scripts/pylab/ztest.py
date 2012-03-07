import pickle

data = pickle.load(open('/home/stone/tmp/banner.p', 'r'))    
print data

for l in data:
    for i in l:
        print i[0] * i[1],
    print
