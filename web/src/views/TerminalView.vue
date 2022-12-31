<template>
  <div id="xterm" class="xterm" />
</template>

<script>

import 'xterm/css/xterm.css'
import { Terminal } from 'xterm'
import { FitAddon } from 'xterm-addon-fit'
import { AttachAddon } from 'xterm-addon-attach'
import { useStore } from "vuex"
import { onMounted, onUnmounted } from 'vue'; // onMounted表示挂载，onUnmounted表示未挂载，即是否有打开当前界面

export default {

    setup() {
        let store = useStore();
        let socketUrl = `ws://${store.state.url.ip}:${store.state.url.port}/websocket/xterm/${store.state.user.token}/`
        let socket = null;
        let term = null;

        onMounted(() => {
            socket = new WebSocket(socketUrl);
            socket.onopen = () => {
                console.log("connected!");
                initback();
            }
            socket.onclose = () => {
                console.log("disconnected!");
            }
            // socket.onmessage = msg => {
            //     console.log(msg);
            // }
            initTerm();
            
            
            
        })

        onUnmounted(() => {
            socket.close();
            term.dispose();
        })

        const initback = () => {
            socket.send("init")
        }

        const write_term = () => {
            term.writeln("hello");
        }

        // const sendmes = mes => {
        //     socket.send(JSON.stringify({
        //         data: mes,
        //         opt: "command",
        //     }))
        // }

        const initTerm = () => {
            term = new Terminal({
                fontSize: 14,
                cursorBlink: true
            });
            const attachAddon = new AttachAddon(socket);
            const fitAddon = new FitAddon();
            term.loadAddon(attachAddon); //用于获取每一个输入字符
            term.loadAddon(fitAddon);
            term.open(document.getElementById('xterm'));
            term.focus()
            term.writeln('Connecting...');
            write_term()
            
        };

        return {

        }
    }
}
</script>

<style>

</style>