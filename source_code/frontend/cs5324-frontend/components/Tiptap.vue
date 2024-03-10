<script setup>
import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'

const editor = useEditor({
    editorProps: {
        attributes: {
            class: 'border border-gray-400 p-4 min-h-[12rem] max-h-[12rem] overflow-y-auto',
        },
        transformPastedText(text) {
            return text.toUpperCase()
        }
    },
    content: "<p>I'm running Tiptap with Vue.js. 🎉</p>",
    extensions: [
        StarterKit,
    ],
})
</script>

<template>
    <div class="container mx-auto max-w-4xl my-8">
        <section class="buttons flex items-center flex-wrap gap-x-4 border-t border-l border-r border-gray-400 p-4">
            <button @click="editor.chain().focus().toggleBold().run()"
                :disabled="!editor.can().chain().focus().toggleBold().run()"
                :class="{ 'bg-baylor-green': editor.isActive('bold') }">
                bold
            </button>
            <button @click="editor.chain().focus().toggleItalic().run()"
                :disabled="!editor.can().chain().focus().toggleItalic().run()"
                :class="{ 'bg-baylor-green': editor.isActive('italic') }">
                italic
            </button>
        </section>
        <EditorContent :editor="editor" />
    </div>
</template>