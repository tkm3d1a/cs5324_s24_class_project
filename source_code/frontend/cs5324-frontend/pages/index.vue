<template>
	<header class="border-4 m-1">
		<nav>
			<ul>
				<li class="border-2 m-1 p-px">
					<NuxtLink to="/">Home</NuxtLink>
				</li>
				<li class="border-2 m-1 p-px">
					<NuxtLink to="/about">About</NuxtLink>
				</li>
				<li class="border-2 m-1 p-px">
					<NuxtLink to="/editor">Editor</NuxtLink>
				</li>
			</ul>
		</nav>
	</header>
</template>
  
<script>
import Tiptap from '../components/Tiptap.vue'

export default {
	name: 'App',
	components: {
		Tiptap
	}
}
</script>