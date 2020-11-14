import random
from decimal import Decimal

print round(Decimal(1) / Decimal(8), 8)
print Decimal(1) / Decimal(3)

while True:
    try:
        print random.randint(2, 5)

        i = 1 / 0
    except BaseException as e:
        print "zzzzzzzzz"
