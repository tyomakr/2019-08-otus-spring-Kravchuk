import axios from 'axios';

const API_URL = '/api/v1/books/';

export default new class ApiService {


    fetchAllBooks() {
        return fetch(API_URL);
    }

    fetchBook(id) {
        return axios.get(API_URL + window.localStorage.getItem(id))
    }

    saveBook(book) {
        return axios.put(API_URL + 'update/', book);
    }

    createBook(data) {
        return axios.post('/api/v1/books/create', data)
    }

    deleteBook(id) {
        return axios.delete(API_URL + id)
    }

    fetchCommentsByBook(id) {
        return axios.get(API_URL + window.localStorage.getItem(id) + '/comments/')
    }


}