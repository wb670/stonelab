
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy.orm.session import sessionmaker
from sqlalchemy.schema import Column, ForeignKey
from sqlalchemy.types import Integer, String
import threading

engine = create_engine('sqlite://', echo=True)
Base = declarative_base(engine)

class Post(Base):
    __tablename__ = 'post'
    id = Column(Integer, primary_key=True)
    name = Column(String)
    post_tags = relationship('PostTag')
    
    def __init__(self, name):
        self.name = name

class Tag(Base):
    __tablename__ = 'tag'
    id = Column(Integer, primary_key=True)
    name = Column(String)
    post_tags = relationship('PostTag')
    
    def __init__(self, name):
        self.name = name
        
class PostTag(Base):
    __tablename__ = 'post_tag'
    post_id = Column(Integer, ForeignKey('post.id'), primary_key=True)
    tag_id = Column(Integer, ForeignKey('tag.id'), primary_key=True)
    tag = relationship('Tag')
    post = relationship('Post')
    
    def __init__(self, tag=None, post=None):
        self.tag = tag
        self.post = post
        
    def __str__(self):
        return '%s %s' % (self.post_id, self.tag_id)

Base.metadata.create_all()

Session = sessionmaker(engine)
session = Session(autoflush=True)

post1 = Post('post1')
tag1 = Tag('tag1')
tag2 = Tag('tag2')
post1.post_tags = [PostTag(tag=tag1), PostTag(tag=tag2)]
session.add(post1)

post2 = Post('post2')
post2.post_tags = [PostTag(tag=tag1), PostTag(tag=tag2)]
session.add(post2)

session.close()

def create():
    Session(autoflush=True)
    
for i in range(1000):
    threading.Thread(target=create).start()
    
