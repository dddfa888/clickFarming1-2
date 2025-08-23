export function notify(options) {
  if (globalThis.$notify) {
    globalThis.$notify(options)
  } else {
    console.warn('Notify not ready')
  }
}
