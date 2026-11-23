"""Summarize JUnit XML results into the job summary and annotate failures."""
import glob
import os
import sys
import xml.etree.ElementTree as ET

label = sys.argv[1] if len(sys.argv) > 1 else "Tests"
totals = {"tests": 0, "failures": 0, "errors": 0, "skipped": 0}
failed = []

for path in glob.glob("build/test-results/**/*.xml", recursive=True):
    root = ET.parse(path).getroot()
    for case in root.iter("testcase"):
        totals["tests"] += 1
        name = "%s.%s" % (case.get("classname"), case.get("name"))
        if case.find("skipped") is not None:
            totals["skipped"] += 1
        for kind in ("failure", "error"):
            node = case.find(kind)
            if node is not None:
                totals[kind + "s"] += 1
                failed.append((name, (node.get("message") or "").splitlines()[:1]))
                print("::error title=%s::%s" % (name, (node.get("message") or kind).splitlines()[0] if node.get("message") else kind))

lines = ["### %s" % label,
         "| Tests | Failed | Errors | Skipped |",
         "|---|---|---|---|",
         "| %(tests)d | %(failures)d | %(errors)d | %(skipped)d |" % totals]
if failed:
    lines += ["", "<details><summary>Failed tests</summary>", ""]
    lines += ["- `%s` %s" % (n, m[0] if m else "") for n, m in failed[:50]]
    lines += ["", "</details>"]

summary = os.environ.get("GITHUB_STEP_SUMMARY")
if summary:
    with open(summary, "a") as out:
        out.write("\n".join(lines) + "\n")
else:
    print("\n".join(lines))
