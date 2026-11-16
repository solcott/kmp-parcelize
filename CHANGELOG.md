# Change Log

## [Unreleased]
[Unreleased]: https://github.com/solcott/kmp-parcelize/compare/1.0.0...HEAD

Changed:
- `@Parcelize` is now an `expect` annotation. On Android it is a typealias to `kotlinx.parcelize.Parcelize`, so Android Lint and Android Studio no longer report a false-positive `ParcelCreator` ("does not provide a `CREATOR` field") error ([#54](https://github.com/solcott/kmp-parcelize/issues/54)). Source compatible; in Android bytecode the annotation is now recorded as `kotlinx.parcelize.Parcelize`.

## [1.0.0] - 2026-04-14
[1.0.0]: https://github.com/solcott/kmp-parcelize/releases/tag/1.0.0

Changed:
- Initial release

