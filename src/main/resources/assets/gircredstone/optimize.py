import os
import json

for root, dirs, files in os.walk("."):
    for file in files:
        if not file.endswith(".json"): continue
        fullPath = os.path.join(root, file)
        with open(fullPath, "r") as fp:
            jobj = json.load(fp)
        with open(fullPath, "w") as fp:
            json.dump(jobj, fp)
