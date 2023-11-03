import openai
import pandas as pd
import os

def chat_restassured(df):

    deployment_name="EnggExGPT35"
    openai.api_type="azure"
    openai.api_key="0f13fd2e937642efb8a422394b85cb9d"
    openai.api_base="https://enggexopenai.openai.azure.com/"
    openai.api_version="2023-07-01-preview"

    #input_parameters = pd.read_excel('API_Template_new.xlsx', sheet_name = 0)
    input_parameters = df

    def createFile(data,j,file_path):
        fp = open(file_path, 'w')
        fp.write(data)
        fp.close()


    rows = len(input_parameters.axes[0])
    prompt1 = '''Write RestAssured test Scripts in Java to validate an API Endpoint. The scripts should perform the following actions.\n'''
    prompt = ''
    for i in range(0, rows):
        base_URL = input_parameters['Base URL'].iloc[i]
        API_Endpoint = input_parameters['API Endpoint'].iloc[i]
        HTTP_Method = input_parameters['HTTP Method'].iloc[i]
        requestHeader = input_parameters['Request Header'].iloc[i]
        requestBody = input_parameters['Request Body'].iloc[i]
        fileLocation = input_parameters['File Location'].iloc[i]
        pathParams = input_parameters['Path Parameters'].iloc[i]
        queryParams = input_parameters['Query/header Parameters'].iloc[i]
        authenticationType = input_parameters['Authentication Type'].iloc[i]
        authenticationCredentials = input_parameters['Authentication Credentials'].iloc[i]
        statusCode = input_parameters['Expected Status Code'].iloc[i]
        responseHeaders = input_parameters['Response Headers'].iloc[i]
        assertions = input_parameters['Assertions'].iloc[i]
        outputVariables = input_parameters['Output Variables'].iloc[i]
        orchestration = input_parameters['Orchestration'].iloc[i]


        prompt = prompt +f'''{i+1}. Set the Base URI for the API to "{base_URL}". \nMake a {HTTP_Method} request to the endpoint "{API_Endpoint}".\n'''
        if not pd.isna(requestHeader):
            prompt = prompt+ f'''with request header {requestHeader}\n'''
        if not pd.isna(requestBody):
            prompt = prompt+ f'''with request body {requestBody}\n'''
        if not pd.isna(fileLocation):
            prompt = prompt+ f'''Upload the file {fileLocation} using multipart().\n'''
        if not pd.isna(pathParams):
            prompt = prompt+ f'''with path parameters {pathParams}\n'''
        if not pd.isna(queryParams):
            prompt = prompt+ f'''with query parameters {queryParams}\n'''
        if not pd.isna(authenticationType):
            prompt = prompt+ f'''With {authenticationType} Authentication type. '''
        if not pd.isna(authenticationCredentials):
            prompt = prompt+ f'''The Authentication Credentials are {authenticationCredentials}\n'''

        prompt = prompt+ '''Assert the following conditions:\n'''
        if not pd.isna(statusCode):
            prompt = prompt+ f'''The response has a status code of {statusCode},\n'''
        else:
            prompt = prompt+ f'''Extract status code,\n'''
        if not pd.isna(responseHeaders):
            prompt = prompt+ f'''{responseHeaders}, '''
        if not pd.isna(assertions):
            prompt = prompt+ f'''{assertions}\n'''
        
        if not pd.isna(outputVariables):
            prompt = prompt+ f'''Extract the {outputVariables} from the response and the test method name.Create a variable outside of the test method and update it.\n'''
        if not pd.isna(orchestration):
            prompt = prompt+ f'''dependsOnMethod "testName", Where {orchestration}, testName are the variables extracted from the previous test.\n'''
    prompt = prompt1 + prompt+ '''Generate all the tests under one class. Only use @test annotation. Make all the necessary imports at the top. Don't use junit imports. 
                                    Give test method names according to the context of the testcase. Don't give test1, test2 and so on. Strictly use "dependsOnMethods" only when it is mentioned in the prompt, don't assume things on your own. 
                                    Provide only the rest assured scripts as testcases. Please don't assume things on your own. Generate scripts only for what is asked\n'''

    prompt2 = f'''Generate an xml file for TestNG report generation using EmailableReporter as listener and com.example.webservices.restservices.APITest as class name.'''
    result_xml = openai.ChatCompletion.create(
        messages=[{"role": "user", "content": prompt2}],
        temperature=0,
        max_tokens= 10000,
        engine= deployment_name
    )
    xml_response=result_xml.choices[0].message.content
    createFile(xml_response, i, file_path= r"D:/GenAI API-testing/scripts/testng.xml")

    result=openai.ChatCompletion.create(
        messages=[{"role": "user", "content": prompt}],
        temperature=0,
        max_tokens= 10000,
        engine= deployment_name
    )
    chat_response=result.choices[0].message.content
    createFile(chat_response, i, file_path= r"D:/GenAI API-testing/scripts/APITest.java")
    return xml_response, chat_response
