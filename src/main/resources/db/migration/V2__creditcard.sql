create table if not exists credit_card_details(
  request_identifier uuid,
  given_name VARCHAR(50) not null,
  card_number VARCHAR(50) not null,
  credit_limit decimal not null,
  created_at timestamp not null,
  modified_at timestamp not null,
  PRIMARY KEY (request_identifier),
  UNIQUE KEY unq_credit_card (card_number)
);

create index if not exists idx_credit_card_details_given_name
ON credit_card_details(given_name);
