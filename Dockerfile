FROM python:3.10

WORKDIR /app

COPY UI.py /app

COPY Testcase_excel_creater.py /app

COPY chat.py /app

RUN pip install -r requirements.txt

CMD ["python", "UI.py"]