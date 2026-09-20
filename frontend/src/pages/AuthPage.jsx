import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';
import { Dumbbell, Lock, Mail, User, AlertCircle, Loader2 } from 'lucide-react';
import './AuthPage.css';

export default function AuthPage() {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    userName: '',
    email: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (isLogin) {
        const response = await api.post('/auth/login', {
          userName: formData.userName,
          password: formData.password,
        });

        const { token } = response.data;
        login(token, { userName: formData.userName });
        navigate('/dashboard');
      } else {
        await api.post('/auth/signup', {
          userName: formData.userName,
          email: formData.email,
          password: formData.password,
        });

        const loginRes = await api.post('/auth/login', {
          userName: formData.userName,
          password: formData.password,
        });

        const { token } = loginRes.data;
        login(token, { userName: formData.userName });
        navigate('/dashboard');
      }
    } catch (err) {
      const message =
        err.response?.data?.message ||
        err.response?.data ||
        'Authentication failed. Please check your credentials.';
      setError(typeof message === 'string' ? message : 'An unexpected error occurred.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <div className="auth-header">
          <div className="auth-icon-wrapper">
            <Dumbbell size={32} color="#4f46e5" />
          </div>
          <h1 className="auth-title">Gym Tracker</h1>
          <p className="auth-subtitle">
            {isLogin
              ? 'Welcome back! Log your lifts and track gains.'
              : 'Create an account to start tracking.'}
          </p>
        </div>

        {error && (
          <div className="auth-error-box">
            <AlertCircle size={18} color="#ef4444" />
            <span className="auth-error-text">{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="auth-input-group">
            <User size={18} className="auth-input-icon" />
            <input
              type="text"
              name="userName"
              placeholder="Username"
              required
              value={formData.userName}
              onChange={handleChange}
              className="auth-input"
            />
          </div>

          {!isLogin && (
            <div className="auth-input-group">
              <Mail size={18} className="auth-input-icon" />
              <input
                type="email"
                name="email"
                placeholder="Email address"
                required
                value={formData.email}
                onChange={handleChange}
                className="auth-input"
              />
            </div>
          )}

          <div className="auth-input-group">
            <Lock size={18} className="auth-input-icon" />
            <input
              type="password"
              name="password"
              placeholder="Password"
              required
              value={formData.password}
              onChange={handleChange}
              className="auth-input"
            />
          </div>

          <button type="submit" disabled={loading} className="auth-submit-btn">
            {loading ? (
              <Loader2 size={18} className="animate-spin" />
            ) : isLogin ? (
              'Sign In'
            ) : (
              'Create Account'
            )}
          </button>
        </form>

        <div className="auth-footer">
          <span>{isLogin ? "Don't have an account?" : 'Already have an account?'}</span>
          <button
            type="button"
            onClick={() => {
              setIsLogin(!isLogin);
              setError('');
            }}
            className="auth-switch-btn"
          >
            {isLogin ? 'Sign Up' : 'Log In'}
          </button>
        </div>
      </div>
    </div>
  );
}