from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, ForeignKey, LargeBinary


class Animation(declarative_base()):
    __tablename__ = "animations"

    id = Column(Integer, primary_key=True)
    textbook = Column(Integer, ForeignKey('textbooks.id'))
    name = Column(String)
    model_file = Column(LargeBinary)


