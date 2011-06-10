'''
Created on 2011-6-5
@author: stone
'''
from MySQLdb.cursors import CursorStoreResultMixIn, BaseCursor

class Dict(dict):
    
    def __getattr__(self, key):
        return self[key]
    
    def __setattr__(self, key, value):
        self[key] = value
    
    def __delattr__(self, key):
        del self[key]

class Cursor(CursorStoreResultMixIn, BaseCursor):

    _fetch_type = 1

    def fetchone(self):
        return Dict(CursorStoreResultMixIn.fetchone(self))

    def fetchmany(self, size=None):
        return (Dict(r) for r in CursorStoreResultMixIn.fetchmany(self, size))

    def fetchall(self):
        return (Dict(r) for r in CursorStoreResultMixIn.fetchall(self))
