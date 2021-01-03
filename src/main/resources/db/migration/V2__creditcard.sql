create table if not exists credit_card_details(
  request_identifier uuid not null,
  given_name VARCHAR(50) not null,
  card_number VARCHAR(20) not null,
  credit_limit decimal not null,
  created_at timestamp not null,
  modified_at timestamp not NULL,
  PRIMARY KEY (request_identifier)
);

create index if not exists idx_credit_card_details_given_name
ON credit_card_details(given_name);
