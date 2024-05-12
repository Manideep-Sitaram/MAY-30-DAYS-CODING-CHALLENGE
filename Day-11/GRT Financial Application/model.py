from langchain_community.document_loaders import PyPDFLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.embeddings import OpenAIEmbeddings
from langchain_community.vectorstores.chroma import Chroma
from langchain.chat_models import ChatOpenAI
from langchain.prompts import ChatPromptTemplate
from langchain.chains.combine_documents import create_stuff_documents_chain
from langchain.chains import create_retrieval_chain
from langchain.output_parsers import StructuredOutputParser
from dotenv import load_dotenv
import os
load_dotenv()


os.environ["LANGCHAIN_TRACING_V2"] = "true"


def get_text_documents():
    
    loader=PyPDFLoader("https://investorcenter.slb.com/static-files/4681ee19-515f-4505-b0ff-8209df9c3553?%20force%C2%A0isolation=true")
    text_documents=loader.load()
    
    return text_documents

def get_text_chunks(text_documents):
    
    text_splitter=RecursiveCharacterTextSplitter(chunk_size=1000,chunk_overlap=200)
    text_chunks=text_splitter.split_documents(text_documents)
    
    return text_chunks

def load_text_chunks_in_vector_databse(text_chunks):
    persist_directory = 'db'
    Chroma.from_documents(text_chunks,OpenAIEmbeddings(),persist_directory=persist_directory)


def user_input(user_query):

    llm=ChatOpenAI(temperature=0.0)
    prompt= ChatPromptTemplate.from_template("""
        Answer the following question based only on the provided context.
        Think step by step before providing on detailed answer.
        I will tip you $1000 if the user finds the answer helpful
        <context>
        {context}
        </context>
        Question: {input}
        """)
    document_chain=create_stuff_documents_chain(llm,prompt)
    db=Chroma(persist_directory='db',embedding_function=OpenAIEmbeddings())
    retriever=db.as_retriever()
    retrieval_chain=create_retrieval_chain(retriever,document_chain)
    response=retrieval_chain.invoke({"input": user_query})
    return response["answer"]
    
def load_documents_initially():
    text_documents = get_text_documents()
    text_chunks = get_text_chunks(text_documents)
    db = load_text_chunks_in_vector_databse(text_chunks)
    