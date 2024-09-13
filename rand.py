import random

plength = int(input("How long"))
yoN = str(input("Do you want special chars? Y/N"))

password = ""
c = []
if(yoN == 'Y' or yoN == 'y'):
    for i in range(33,125):
        c.append(i)
else:
    for i in range(48,57):
        c.append(i)
    for i in range(65,90):
        c.append(i)
    for i in range(97,122):
        c.append(i)
for i in range(plength):
    password = password + chr(c[random.randint(0,len(c)-1)])

print(password)