import axios, { AxiosResponse } from "axios";

const axiosInst = axios.create({
  baseURL: "http://localhost:9092/api/v1/",
  timeout: 2000,
});

export const request = (
  method: string,
  url: string,
  data?: any,
  token?: string | null
): Promise<AxiosResponse<any, any>> => {
  let headers = {};
  if (token) {
    headers = { Authorization: `Bearer ${token}` };
  }

  return axiosInst({
    method: method,
    url: url,
    data: data,
    headers: headers,
  });
};
