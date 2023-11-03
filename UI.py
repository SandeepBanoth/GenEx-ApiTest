import streamlit as st
import pandas as pd
 

st.title(":rainbow[GenEx-APiTest]🛠️🗃️💻")

 
st.markdown("**This is a web-based application that allows users to generate GenAI based Intelligent RestAssured Scripts for API Testing**")
 
st.write(":blue[Step 1: Upload the Swagger OpenAPI Sepcification]")
uploaded_file = st.file_uploader("Upload your Swagger file (YAML format)", type=".txt")
 
testbutton=st.button("Generate Testcases")
 
if testbutton:
    file_content = uploaded_file.read()
    import Testcase_excel_creator
    Testcase_excel_creator.test_excel_creator(file_content)
    st.write(":blue[Your testcases have been generated!]")
    st.write("Download the Excel file containing the testcases:")
 
st.write(":blue[Step 2: Download the TestCases Excel]")
template_path = "D:\GenAI API-testing\TestCases\Test_cases.xlsx"
download_button = st.download_button(label="Download", data=open(template_path, 'rb'),file_name="API_Testing_Template.xlsx", mime='application/vnd.ms-excel')
 
st.write(":blue[Step 3: Upload File]")
uploaded_file1 = st.file_uploader("Upload Filled Out Template", type=".xlsx")
 
generate=st.button("Generate Scripts")

if generate:
    import chat
    df=pd.read_excel(uploaded_file1)
    a,b=chat.chat_restassured(df)
    st.write(":red[XML file:]")
    st.code(a)
    st.write(":red[APITest.java:]")
    st.code(b)