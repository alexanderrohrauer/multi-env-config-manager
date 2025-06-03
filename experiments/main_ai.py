from dotenv import load_dotenv
load_dotenv("/Users/alexanderrohrauer/IdeaProjects/EA-Chatbot-mit-RAG/backend/.env")
import os
from pathlib import Path
from openai import OpenAI

configs_path = Path("./configs")

client = OpenAI(
    #base_url="http://localhost:11434/v1",
    api_key=os.getenv("OPENAI_API_KEY")
)

file_name = input("Filename:")

config_path = configs_path / file_name
base = open(config_path, "r").read()
overlay_path_split = str(config_path).split(".")
ext = overlay_path_split[1]
overlay_path = overlay_path_split[0] + "_overlay." + overlay_path_split[1]
overlay = open(overlay_path, "r").read()

prompt = f"""
You are a system that merges two config files. Always prioritize the second config.

Always mind the following rules:
- Just output the merged config, without any side-notes and as plain-text. No markdown!
- There is a directive prefixed with "mcfg.io <instruction>" that gives you additional instructions on how you should merge the specific block. Remove the directive in the target.
- The "patch_<content>" directive tells you that just the content should be patched.

```{ext}
{base}
```

```{ext}
{overlay}
```
"""

print(prompt)

message = {"role": "user", "content": prompt}

response = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[message]
)

out = response.choices[0].message.content.strip()

print(out)
out_path = overlay_path_split[0] + "_out." + overlay_path_split[1]
open(out_path, "w").write(out)
