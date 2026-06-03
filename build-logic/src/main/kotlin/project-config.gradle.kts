// Shared configuration applied to every project via the convention plugins (and the root build).
// Centralizes ktfmt so we no longer apply plugins in an allprojects {} block.
plugins { id("com.ncorti.ktfmt.gradle") }

ktfmt {
  googleStyle()
  removeUnusedImports = true
}
