import random, time, pdb

debugger = pdb.Pdb()

def add(i , j):
    ret = i + j 
    return ret

def index():
    while(True):
        debugger.set_trace()
        i = random.randint(1, 10)
        j = random.randint(1, 10)
        ret = add(i, j)
        print ret
        
        time.sleep(ret / 5)

index()
