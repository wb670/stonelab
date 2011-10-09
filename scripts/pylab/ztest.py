import time

def timeit(name):
    def decorator(func):
        def wrapper(*args, **kwargs):
            start = time.time()
            func(*args, **kwargs)
            print '%s used: %f' % (name, time.time() - start)
        return wrapper
    return decorator

def logit(name):
    def decorator(func):
        def wrapper(*args, **kwargs):
            print '%s excuted' % (name)
            func(*args, **kwargs)
        return wrapper
    return decorator

@timeit('Foo')
@logit('Foo')
def foo(seconds):
    time.sleep(seconds)
    print 'foo Done'
    
foo(2)
