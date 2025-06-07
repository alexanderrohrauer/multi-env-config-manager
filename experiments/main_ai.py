from dotenv import load_dotenv
load_dotenv("/Users/alexanderrohrauer/IdeaProjects/EA-Chatbot-mit-RAG/backend/.env")
import os
from pathlib import Path
from openai import OpenAI
import re

configs_path = Path("./configs")

client = OpenAI(
    base_url="http://localhost:11434/v1",
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
You are a system that just merges two config files. Always prioritize the second config in case there are conflicts. No matter the type of the merged field.

Always mind the following rules:
- There is a directive prefixed with "mcfg.io <instruction>" that gives you additional instructions on how you should merge the specific block. Remove the directive in the merged config.
- The "patch_<content>" directive tells you that just the content (e.g. the item) should be swapped out.
- The "append_<content>" directive tells you that just the content (e.g. the item) should be appended.
- Do not alter or add anything else to the config (no comments etc.)

In the case of conflicting sections without additional mcfg.io annotations take the second config, e.g.:

```gradle
tasks {"{"}
    task1()
{"}"}

version = "1.0"
```

```gradle
tasks {"{"}
    task2()
{"}"}
```

the results is

```gradle
tasks {"{"}
    task2()
{"}"}

version = "1.0"
```

Here are the configs:

```{ext}
{base}
```

```{ext}
{overlay}
```

Just give out the merged config as plain-text. Never give any additional information or side notes and do not format it somehow (e.g. in markdown)
"""

print(prompt)

message = {"role": "user", "content": prompt}

response = client.chat.completions.create(
    #model="gpt-4o-mini",
    model="deepseek-coder-v2:16b",
    messages=[message]
)

out = response.choices[0].message.content.strip()

group = re.search(r"```[^\n]+\n([\s\S]*)```", out)

out = group.group(1) if group is not None else out

print(out)
out_path = overlay_path_split[0] + "_out." + overlay_path_split[1]
open(out_path, "w").write(out)
