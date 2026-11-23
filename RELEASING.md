# Releasing

After the Build workflow passes on a push to `main`, the current `-SNAPSHOT` version is published
to the Maven Central snapshots repository (see `.github/workflows/snapshot.yml`).

Both workflows read repository secrets (Settings > Secrets and variables > Actions):
`ORG_GRADLE_PROJECT_MAVENCENTRALUSERNAME`, `ORG_GRADLE_PROJECT_MAVENCENTRALPASSWORD`,
`ORG_GRADLE_PROJECT_SIGNINGINMEMORYKEY`, `ORG_GRADLE_PROJECT_SIGNINGINMEMORYKEYID`,
`ORG_GRADLE_PROJECT_SIGNINGINMEMORYKEYPASSWORD`, plus `GRADLE_PUBLISH_KEY` and
`GRADLE_PUBLISH_SECRET` for releases.

To cut a release:

1. Update `version` in `gradle.properties` to the release version (drop the `-SNAPSHOT` suffix).

2. Update the `CHANGELOG.md`:
    1. Change the `Unreleased` header to the release version.
    2. Add a link URL to ensure the header link works.
    3. Add a new `Unreleased` section to the top.

3. Commit

   ```
   $ git commit -am "Prepare version X.Y.Z"
   ```

4. Tag

   ```
   $ git tag -am "Version X.Y.Z" X.Y.Z
   ```

5. Update `version` in `gradle.properties` to the next development version (e.g. `X.Y+1.0-SNAPSHOT`).

6. Commit

   ```
   $ git commit -am "Prepare next development version"
   ```

7. Push!

   ```
   $ git push && git push --tags
   ```

   This will trigger a GitHub Action workflow which will create a GitHub release and upload the
   release artifacts to Maven Central.