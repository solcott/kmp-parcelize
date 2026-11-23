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

7. Push the commits through a pull request. `main` requires one, and it must be merged with a
   merge commit (not squash or rebase) so the tagged commit stays in `main`'s history.

   ```
   $ git push origin HEAD:release/X.Y.Z
   $ gh pr create --base main --head release/X.Y.Z --title "Release X.Y.Z"
   $ gh pr merge --merge --delete-branch
   ```

8. Push the tag

   ```
   $ git push origin X.Y.Z
   ```

9. Create a GitHub release for the tag

   ```
   $ gh release create X.Y.Z --verify-tag --title X.Y.Z --notes-file notes.md
   ```

   Publishing the release triggers the Release workflow (`.github/workflows/release.yml`). It
   uploads the runtime and plugin to Maven Central, waits for Central Portal to validate them and
   releases them automatically, then publishes the plugin to the Gradle Plugin Portal. Artifacts
   usually show up on Maven Central 10 to 30 minutes later.
