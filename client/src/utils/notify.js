import { createVNode, render } from 'vue'
import Notify from '../components/Notify.vue'

export function notify({ title = '', message = '', type = 'info', duration = 3000 }) {
    const container = document.createElement('div')
    document.body.appendChild(container)

    const vnode = createVNode(Notify, {
        title,
        message,
        type,
        duration,
        onClose: () => {
            render(null, container)
            document.body.removeChild(container)
        }
    })

    render(vnode, container)
}
