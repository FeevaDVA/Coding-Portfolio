#scrape the all set information from serebii.net and make a database
import os
import requests
from bs4 import BeautifulSoup

url = "https://serebii.net/card/japanese.shtml"

r = requests.get(url, headers={'user-agent': 'Mozilla/5.0'})
soup = BeautifulSoup(r.content, 'html.parser')

setsURL = soup.find_all('tr')
setsURL.pop(0)
tempSets = []
for x in setsURL:
    setID = x.find('a').get('href')
    tempSets.append("https://serebii.net" + setID)
setsURL = tempSets

for x in setsURL:
    r = requests.get(x, headers = {'user-agent': 'Mozilla/5.0'})
    soup = BeautifulSoup(r.content, 'html.parser')

    setName = soup.find('u').contents[0]
    setName = setName.replace(':', '')

    path = os.path.normpath("/Pokemon Card Reader/db/" + setName + "/")
    if not os.path.exists(path):
        os.mkdir(path)
    else:
        setName = setName + "-2"
        path = os.path.normpath("/Pokemon Card Reader/db/" + setName + "/")
        os.mkdir(path)

    print(setName)

    cards = soup.find_all('td', class_="cen", width = "142")

    for img in cards:
        link = 'https://serebii.net' + img.findChild('img').get('src')
        image = requests.get(link)
        filename = link.split('/')[-1]

        path2 = os.path.normpath("/Pokemon Card Reader/db/" + setName + "/" + filename + "/")

        if not os.path.exists(path2):
            file = open(path2, "wb")
            file.write(image.content)
            file.close()
