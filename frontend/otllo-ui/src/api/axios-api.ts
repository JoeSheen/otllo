import axios, { AxiosResponse } from "axios";

const axiosInst = axios.create({
  baseURL: "http://localhost:9092/api/v1/",
  timeout: 2000,
});

export const request = (
  method: string,
  url: string,
  data: any
): Promise<AxiosResponse<any, any>> => {
  return axiosInst({
    method: method,
    url: url,
    data: data,
  });
};
