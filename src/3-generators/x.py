
def create_generator():
    print("generator started")
    yield 1
    print("generator finished")


generator = create_generator()
print(type(generator))
try:
    next(generator)
    next(generator)
except StopIteration:
    print("main StopIteration")
finally:
    generator.close() # can be caught as GeneratorExit
