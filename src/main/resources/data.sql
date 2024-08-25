-- Tipos de solicitação
INSERT INTO tipo_solicitacao (descricao) VALUES ('Cartões');                    -- Tipo 1
INSERT INTO tipo_solicitacao (descricao) VALUES ('Empréstimos');                -- Tipo 2
INSERT INTO tipo_solicitacao (descricao) VALUES ('Suporte');                    -- Tipo 3
INSERT INTO tipo_solicitacao (descricao) VALUES ('Outros assuntos');            -- Tipo 4

-- Times
INSERT INTO time (nome, time_padrao) VALUES ('Cartões', false);         -- Time 1
INSERT INTO time (nome, time_padrao) VALUES ('Empréstimos', false);     -- Time 2
INSERT INTO time (nome, time_padrao) VALUES ('Outros assuntos', true);  -- Time 3

-- Times x Tipos de solicitação
INSERT INTO time_tipo_solicitacao (time_id, tipo_solicitacao_id) VALUES (1, 1);
INSERT INTO time_tipo_solicitacao (time_id, tipo_solicitacao_id) VALUES (2, 2);

-- Atendentes
INSERT INTO atendente (nome, time_id, ativo) VALUES ('Atendente A', 1, true);
INSERT INTO atendente (nome, time_id, ativo) VALUES ('Atendente B', 1, true);
INSERT INTO atendente (nome, time_id, ativo) VALUES ('Atendente C', 2, true);
INSERT INTO atendente (nome, time_id, ativo) VALUES ('Atendente D', 3, false);
INSERT INTO atendente (nome, time_id, ativo) VALUES ('Atendente E', 3, true);
INSERT INTO atendente (nome, time_id, ativo) VALUES ('Atendente F', 3, true);