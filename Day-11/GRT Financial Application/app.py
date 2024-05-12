import streamlit as st
from model import *

st.title("GRT Financials Application")

load_document = st.button("Load Document")

user_query = st.text_input("Please Enter Your Query")

if load_document:
    load_documents_initially()

if user_query:
    st.write((user_input(user_query)).replace("$","\$"))