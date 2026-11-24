import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'pojo-tester',
  description: 'Tests equals, hashCode, toString, getters, setters and constructors.',
  lang: 'en-US',
  cleanUrls: true,
  lastUpdated: true,
  ignoreDeadLinks: [/^\/javadoc/],
  themeConfig: {
    siteTitle: 'pojo-tester',
    nav: [
      { text: 'Guide', link: '/guide' },
      { text: 'Comparison', link: '/comparison' },
      { text: 'Changelog', link: '/changelog' },
      { text: 'Javadoc', link: '/javadoc/' }
    ],
    sidebar: [
      {
        text: 'Documentation',
        items: [
          { text: 'Introduction', link: '/' },
          { text: 'Writing tests', link: '/guide' },
          { text: 'Comparison', link: '/comparison' },
          { text: 'Changelog', link: '/changelog' },
          { text: 'Contributors', link: '/contributors' },
          { text: 'Fork survey', link: '/forks' }
        ]
      }
    ],
    socialLinks: [
      { icon: 'github', link: 'https://github.com/sta-szek/pojo-tester' }
    ],
    editLink: {
      pattern: 'https://github.com/sta-szek/pojo-tester/edit/master/docs/:path',
      text: 'Edit this page'
    },
    footer: {
      message: 'Released under the LGPL-3.0.',
      copyright: 'Copyright © 2016–2026 Piotr Joński'
    },
    search: {
      provider: 'local'
    }
  }
})
