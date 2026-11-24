import { readFile, writeFile } from 'node:fs/promises'

const changelog = await readFile(new URL('../../CHANGELOG.md', import.meta.url), 'utf8')
await writeFile(
  new URL('../changelog.md', import.meta.url),
  `---\neditLink: false\n---\n\n${changelog}`
)

const headers = {
  Accept: 'application/vnd.github+json',
  'User-Agent': 'pojo-tester-docs',
  'X-GitHub-Api-Version': '2022-11-28'
}
const token = process.env.GITHUB_TOKEN || process.env.GH_TOKEN
if (token) {
  headers.Authorization = `Bearer ${token}`
}

let people = []
try {
  const response = await fetch(
    'https://api.github.com/repos/sta-szek/pojo-tester/contributors?per_page=100',
    { headers }
  )
  if (!response.ok) {
    throw new Error(`${response.status} ${response.statusText}`)
  }
  people = await response.json()
} catch (error) {
  console.warn(`Contributors were not loaded: ${error.message}`)
}

const rows = people
  .filter((person) => person.type === 'User' && !person.login.endsWith('[bot]') && person.login !== 'gitbook-bot')
  .map((person) => `| [${person.login}](${person.html_url}) | ${person.contributions} |`)
  .join('\n')

const body = rows
  ? `| Contributor | Commits |\n| --- | ---: |\n${rows}\n`
  : 'The contributor list could not be loaded for this build. It is on the [repository](https://github.com/sta-szek/pojo-tester/graphs/contributors).\n'

await writeFile(
  new URL('../contributors.md', import.meta.url),
  `---
editLink: false
---

# Contributors

People with commits on [\`sta-szek/pojo-tester\`](https://github.com/sta-szek/pojo-tester). The count is GitHub's, for the default branch.

${body}
`
)
