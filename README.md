# _Sadapay Exercise_

The goal of this assignment is to build a simple single-screen app that shows the current trending Github repositories fetched from a public API. The design and business specifications have been provided below. We have deliberately kept the app simple enough for everyone to attempt it but we are keen to see the approach you take to solve it. You have the freedom to give your best to it and demonstrate your skills for us to evaluate you better. You should approach this problem as if you are building an MVP app for our production users - the process you follow and the things you should consider should reflect how you would approach this solution if you were to be working for us on something we are going to release to customers.

## _Assumptions and notes_

> I am stalling the data via network interceptor. At the very start, I thought we can achieve data staling using Room DB to save the API cost. In fact, I had started implementing the local and remote repository and writing the test cases before actual implementation but there was doubt knocking in my head that I am doing something wrong for data staling. It should not be much hectic to show the stale data. Saving data in room DB was really time taking. It also increases the app size and there are some scalability issues as well, So I thought more about the data stalling and then I thought why don't we use caching with retrofit and finally came up with the network interceptor with `Cache-Control: public, max-stale=SECONDS`

`Note: `I have added an options menu to get the current data.

`I have strongly followed RGR (Red, Green, Refactor) while doing TDD`

### _Tech_

- _MVVM_
- _Kotlin_
- _Coroutines_
- _TDD_
- _Lottie_
- _mockk (for testing)_
- _Hilt_

## _Features_

- Show trending GitHub repositories in the list.
- Stale data support.
- Dark mode support.
- Shimmer animation.
## _Architecture_

> I have structured the architecture keeping in mind the SOLID principles and clean architecture. Initially, I started with the architecture base that I have developed previously, and then, while, keeping in mind these principles, I improvised and improved the overall structure and flow.

## _TDD_
> I have tried to gain as maximum code coverage as possible. I tried to write the UI automation tests as much as I should have. I have written test cases.
- ViewModel
- Repository
- Activity UI