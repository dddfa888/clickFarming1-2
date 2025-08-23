// postcss.config.js
export default {
    plugins: {
        'postcss-px-to-viewport': {
            viewportWidth: 375,
            unitPrecision: 5,
            viewportUnit: 'vw',
            selectorBlackList: ['ignore', 'van-'],
            minPixelValue: 1,
            mediaQuery: false
        }
    }
}
