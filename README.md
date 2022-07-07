# shotclockapp

Simple basketball shot clock app .

## Getting started

### Development setup

Android Studio 3.0 or newer.

##

### Built with

* [Kotlin](https://kotlinlang.org/) - App is purely written in kotlin.
* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
* [Jetpack Compose](https://developer.android.com/jetpack/compose)

### Inspiration

I found myself with nothing to do on a Saturday morning. Since me and my friends play basketball
quite often and we usually just use shot clock apps that are already available in play store. So, I
thought, "why not create one of my own?". That's when I decided to create this app.

<a href='https://play.google.com/store/apps/details?id=jermaine.shotclockapp&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height=150 width=400/></a>

### TODO

1. Update tech stack of the app
    * Coroutines - Done
    * Update Architecture (ViewModels, Package by Feature) - Done
    * Jetpack Compose
        * Main - Done
        * Settings - DONE
2. Add UI Tests
    - [x] Initial state should have timer to 24
    - [x] +1 button should increment timer
    - [x] +1 button should NOT increment timer IF timer is equal to 24
    - [x] +1 button should NOT increment timer IF timer is equal to 14
    - [x] -1 button should decrement timer
    - [x] -1 button should NOT decrement timer IF timer is equal zero
    - [x] Reset button should reset timer to 24
    - [x] Reset button should reset timer to 14
    - [x] Swiping left should change page to 14
    - [x] Swiping right should change page to 24
    - [x] Clicking 14 page preview should change page to 14
    - [x] Clicking 24 page preview should change page to 24
    - [x] Clicking settings should navigate to settings
    - [ ] Test theme change
3. Add Unit Tests
    - [x] Play button should play timer
    - [x] Play button should pause timer
    - [x] Timer should reset to 24 when timer is completed
    - [x] Timer should reset to 14 when timer is completed
4. Add ktlint pre-commit hook
5. Setup CI/CD on Bitrise
