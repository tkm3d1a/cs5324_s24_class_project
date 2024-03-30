-- Run this to insert a POST for the test_admin user in DB to test endpoints
INSERT INTO public.post(id, content, created_at, is_tagged, status, title, version, user_id)
VALUES (gen_random_uuid(), 'Test Post Contains Lorem Epsum',
        '2024-03-29 11:30:30', '0', 'PENDING', 'Post Tile 1', 1.0,
        '2bcc6af8-4c97-4afc-96af-b03a128e8a32');