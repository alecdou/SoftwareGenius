import urllib.request
import re
from bs4 import BeautifulSoup #这里需要导入BeautifulSoup 
url="https://pakmcqs.com/category/software-engineering-mcqs/design-and-implementation/page/6"
content=urllib.request.urlopen(url)
soup=BeautifulSoup(content) #将网页内容转化为BeautifulSoup 格式的数据
text=soup.get_text()[462:]
result=re.split('0|Read More Details about this Mcq',text)
# result=text.split('Read More Details about this Mcq')
# result=result.split('0')
# for i in result:
#     i=i.strip()
# print (result)
questions=[]
result=result[:-5]
print(result,len(result))
for i in range(len(result)):
    if i%2==0:
        question={}
        question['question']='\''+result[i]+'\''
    else:
        index=[]
        for j in range(len(result[i])-2):
            if result[i][j:j+2]=='A.' or result[i][j:j+2]=='B.' or result[i][j:j+2]=='C.' or result[i][j:j+2]=='D.':
                index.append(j)
        print(index)
        try:
            question['choice1']='\''+result[i][index[0]:index[1]]+'\''
            question['choice2']='\''+result[i][index[1]:index[2]]+'\''
            question['choice3']='\''+result[i][index[2]:index[3]]+'\''
            question['choice4']='\''+result[i][index[3]:]+'\''
        except:
            continue
        
        # question['type']='QA'
        # difficultyLevel=1
        # question['userAnswered']=0
        # question['userCorrect']=0
        questions.append(question)
print(questions,len(questions))

for i in questions:
    print('insert into question(category, problem,choice1,choice2,choice3,choice4,answer,difficultyLevel,userAnswered,userCorrect) VALUES ({},{},{},{},{},{},1,1,0,0);'.format('\'SA\'',i['question'],i['choice1'],i['choice2'],i['choice3'],i['choice4']))
