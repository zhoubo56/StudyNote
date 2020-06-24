
KEY_FILE=tls.key
CERT_FILE=tls.crt
HOST=nginxsvc
CERT_NAME=tls-secret

openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ${KEY_FILE} -out ${CERT_FILE} -subj "/CN=${HOST}/O=${HOST}"

kubectl create secret tls ${CERT_NAME} --key ${KEY_FILE} --cert ${CERT_FILE}