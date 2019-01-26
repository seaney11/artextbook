from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String


class Textbook(declarative_base()):
    __tablename__ = "textbooks"

    id = Column(Integer, primary_key=True)
    name = Column(String)
    barcode = Column(String)


