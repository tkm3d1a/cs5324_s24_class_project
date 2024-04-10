<template>
	<div class="min-h-screen flex flex-col">
		<Navigation />
		<div class="flex-grow flex flex-col justify-center items-center">
			<div class="border-2 border-black w-1/2 flex flex-col items-center pt-12 pb-24">
				<div class="w-full px-6">
					<label for="title">Title</label>
					<input type="text" name="title" id="title" class="w-full border-2 border-black rounded-md" />
				</div>

				<span class="py-2" />

				<div class="h-96 w-full px-6">
					<label for="content">Content</label>
					<input type="text" name="content" id="content" class="border-2 border-black rounded-md w-full h-5/6" />
				</div>

				<div class="w-full flex justify-between px-10">
					<button class="rounded-xl h-12 w-36 bg-baylor-green text-baylor-gold font-bold">Upload Media</button>
					<button class="rounded-xl h-12 w-36 bg-baylor-green text-baylor-gold font-bold">Submit</button>
				</div>
			</div>
		</div>
	</div>
</template>
